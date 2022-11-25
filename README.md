# Android MVVM

![MVVM](https://cdn-images-1.medium.com/fit/t/1600/480/1*kWwjlkOEyTV6M7W7tZrs1w.png)

## MVVM stands for **Model, View, ViewModel.**

* **Model:** This holds the data of the application. It cannot directly talk to the View. Generally,
  it’s recommended to expose the data to the ViewModel through Observables.

* **View:** It represents the UI of the application devoid of any Application Logic. It observes the
  ViewModel.

* **ViewModel:** It acts as a link between the Model and the View. It’s responsible for transforming
  the data from the Model. It provides data streams to the View. It also uses hooks or callbacks to
  update the View. It’ll ask for the data from the Model.

## The following flow illustrates the core MVVM Pattern

![MVVM](https://cdn.journaldev.com/wp-content/uploads/2018/04/android-mvvm-pattern.png)

## How does this differ from MVP?

* ViewModel replaces the Presenter in the Middle Layer.
* The Presenter holds references to the View. The ViewModel doesn’t.
* The Presenter updates the View using the classical way (triggering methods).
* The ViewModel sends data streams.
* The Presenter and View are in a 1 to 1 relationship.
* The View and the ViewModel are in a 1 to many relationship.
* The ViewModel does not know that the View is listening to it.

## For more information about this architecture code, you can refer to some of these links

* [Architecture Code](https://github.com/maiconhellmann/kotlin-mvvm-coroutines-koin)
* [Ready for Koin 2.0](https://medium.com/koin-developers/ready-for-koin-2-0-2722ab59cac3)
* [Android Data Binding](https://medium.com/@temidjoy/android-jetpack-empower-your-ui-with-android-data-binding-94a657cb6be1)
* [Android VIP](https://speakerdeck.com/androidvip)
* [Multidex](https://proandroiddev.com/android-jetpack-foundation-multidex-part-1-chapter-3-64a3e43c92dc)
* [Modularizing Android Applications](https://medium.com/google-developer-experts/modularizing-android-applications-9e2d18f244a0)