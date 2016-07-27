# ToolBox

Para instalar hay que agregar estas lineas al gradle de la app

repositories {
    maven {
        url 'https://dl.bintray.com/jojojr/maven/'
    }
}

...

dependencies {
    compile 'gemontenegro:toolboxlib:0.0.1'
}
