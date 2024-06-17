package com.example.appmusickotlin.model


object User  {
    var username: String? = ""
    var email: String? = ""
    var password: String? = ""
    var rePassword: String? = ""
    var albumsLst: MutableList<DataListPlayList> = mutableListOf<DataListPlayList>()
}

