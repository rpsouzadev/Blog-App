package com.rpsouza.blogapp.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.rpsouza.blogapp.R
import com.rpsouza.blogapp.domain.model.MapAppOption
import java.net.URLDecoder
import java.net.URLEncoder

fun findMapApps(
    context: Context,
    addressLabel: String,
    addressQuery: String,
    lat: Double? = null,
    lng: Double? = null,
): List<MapAppOption> {
    val pm = context.packageManager

    val encodedQ = URLDecoder.decode(
        lat?.let { "$addressQuery $lat,$lng" } ?: addressQuery,
        Charsets.UTF_8.name()
    )

    val geoUri = if (lat != null && lng != null)
        Uri.parse("geo:$lat,$lng?q=$encodedQ")
    else
        Uri.parse("geo:0,0?q=$encodedQ")

    val genericView = Intent(Intent.ACTION_VIEW, geoUri)

    val resolveInfos = pm.queryIntentActivities(genericView, PackageManager.MATCH_DEFAULT_ONLY)

    // Pacotes “conhecidos” para aplicar ícone/rotas custom
    val PKG_MAPS  = "com.google.android.apps.maps"
    val PKG_WAZE  = "com.waze"
    val PKG_UBER  = "com.ubercab"

    // Monta base para todos (ícone/label padrão do sistema)
    val options = mutableMapOf<String, MapAppOption>()
    resolveInfos.forEach { info ->
        val label = info.loadLabel(pm)?.toString().orEmpty()
        val icon = info.loadIcon(pm)

        val component = ComponentName(info.activityInfo.packageName, info.activityInfo.name)
        val appIntent = Intent(genericView)
            .setComponent(component)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        options[info.activityInfo.packageName] = MapAppOption(label, icon, appIntent)
    }

    // Substitui/insere versões “premium” com seu DS e deep links específicos:
    fun dsIcon(@DrawableRes res: Int): Drawable =
        AppCompatResources.getDrawable(context, res)!!

    // Google Maps (deep link de direção)
    if (isInstalled(pm, PKG_MAPS)) {
        val mapsUri = if (lat != null && lng != null)
            Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng&destination_place_id=")
        else
            Uri.parse("https://www.google.com/maps/search/?api=1&query=${URLEncoder.encode(addressQuery, "UTF-8")}")

        val intent = Intent(Intent.ACTION_VIEW, mapsUri)
            .setPackage(PKG_MAPS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        options[PKG_MAPS] = MapAppOption(
            label = "Google Maps",
            icon = dsIcon(R.drawable.outline_add_location_24), // ícone do seu DS
            launchIntent = intent
        )
    }

    // Waze (deep link de navegação)
    if (isInstalled(pm, PKG_WAZE)) {
        val wazeUri = if (lat != null && lng != null)
            Uri.parse("https://waze.com/ul?ll=$lat,$lng&navigate=yes")
        else
            Uri.parse("https://waze.com/ul?q=${URLEncoder.encode(addressQuery, "UTF-8")}&navigate=yes")

        val intent = Intent(Intent.ACTION_VIEW, wazeUri)
            .setPackage(PKG_WAZE)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        options[PKG_WAZE] = MapAppOption(
            label = "Waze",
            icon = dsIcon(R.drawable.outline_add_location_24),
            launchIntent = intent
        )
    }

    // Uber (não responde geo:, então tratamos à parte)
    if (isInstalled(pm, PKG_UBER)) {
        val uberUri = if (lat != null && lng != null)
            Uri.parse("uber://?action=setPickup&pickup=my_location&dropoff[latitude]=$lat&dropoff[longitude]=$lng&dropoff[nickname]=${URLEncoder.encode(addressLabel, "UTF-8")}")
        else
        // fallback: endereço textual (nem sempre perfeito)
            Uri.parse("uber://?action=setPickup&pickup=my_location&dropoff[formatted_address]=${URLEncoder.encode(addressQuery, "UTF-8")}")

        val intent = Intent(Intent.ACTION_VIEW, uberUri)
            .setPackage(PKG_UBER)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        options[PKG_UBER] = MapAppOption(
            label = "Uber",
            icon = dsIcon(R.drawable.outline_add_location_24),
            launchIntent = intent
        )
    }

    val ordering = listOf(PKG_MAPS, PKG_WAZE, PKG_UBER)

    return options.values.sortedWith(
        compareBy(
            { ordering.indexOf(it.launchIntent.`package` ?: "").let { idx -> if (idx == -1) Int.MAX_VALUE else idx } },
            { it.label.lowercase() }
        )
    )
}

private fun isInstalled(pm: PackageManager, pkg: String): Boolean =
    try { pm.getPackageInfo(pkg, 0); true } catch (_: Exception) { false }