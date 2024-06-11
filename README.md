﻿# <h1 align="center">Blog App</h1>

<div width=100% align='center'>
  <a href = "https://developer.android.com/?hl=pt-br"><img src="https://img.shields.io/badge/-Android-%3DDC84?style=for-the-badge&logo=android&logoColor=white" target="_blank"></a>
  <a href="https://kotlinlang.org/" target="_blank"><img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" target="_blank"></a>
</div>

 O blog app é um desafil para vaga de desenvolvedor android jr, onde o app simula um blog consumindo a api https://jsonplaceholder.typicode.com/. E nesse dasafio foi rquerido as seguintes tarefas:

- Exibir uma lista retornada da api
- Criar uma interface propria
- Clicar em um item da lista e ir para outra tela mostrado as infamções completas do item
- Persistir os dados da api, localmente caso o usario esteja sem internet
- Desenvolver o app no android studio

#

<h3 align="center">App</h1>

<div style="display: flex; justify-content: center; flex-wrap: wrap;" align='center'>
    <img src="https://raw.githubusercontent.com/rpsouzadev/Blog-App/main/screenshots/home.png" alt="" style="width: 320px;" />
    <img src="https://raw.githubusercontent.com/rpsouzadev/Blog-App/main/screenshots/details.png" alt="" style="width: 320px;" />
</div>


## 🔨 Tecnologias:

- [Android Studio](https://developer.android.com/studio?gclid=Cj0KCQjw8NilBhDOARIsAHzpbLB0GuTo3RQmKSb001eorQmvKXhn-LesqjAh19LcYgpl6WjE9JGxj9EaAmfmEALw_wcB&gclsrc=aw.ds)
- [Kotlin](https://kotlinlang.org/)
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Navigation Safe Args](https://developer.android.com/guide/navigation/use-graph/safe-args)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)

<hr>
 
 > Obs: Para a aplicação é necessário que você tenha variaveis de ambiente: BASE_URL e SHARED_KEY adicioce essas duas variaveis no local.properties, sendo a prima a base url da api e a segunda a chave para salvar os dados com o sharedpreferences
<hr>
