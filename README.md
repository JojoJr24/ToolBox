# ToolBox

Para instalar hay que agregar estas lineas al gradle de la app

repositories {
    maven {
        url 'https://dl.bintray.com/jojojr/maven/'
    }
    maven {
    url 'https://oss.sonatype.org/content/repositories/ksoap2-android-releases/'
    }
}

...

dependencies {
    compile 'gmontenegro:toolboxlib:0.0.1'
}


En la carpeta de Assets de la applicación hay que agregar un archivo statAsset que contenga un JSon
donde los siguientes parametros son obligatorios

{
  "debug": "true",
  "saveLog": "true"
  "debugLevel": "3"
}

Si se requieren mas parametros por default deben agregarse al archivo y sobrecargar la clase
DefaultSettings con las variables de los nuevos parametros.
Y sobrecargar el metodo