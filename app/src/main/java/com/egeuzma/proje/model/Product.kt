package com.egeuzma.proje.model

class Product(name : String , unitType : String,category :String,unitCalorie:Number) {
    var isim :String ? = name
        private set
        get
    var type :String ? = unitType
        private set
        get
    var calorie :Number ? = unitCalorie
        private set
        get
    /*var name_en :String ? = name_en
        private set
        get
    var kategori_en :String ? = category_en
        private set
        get*/
    var kategori :String ? = category
        private set
        get
}