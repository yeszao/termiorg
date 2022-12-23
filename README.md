
```shell
#export JAVA_HOME=/Users/gary/Library/Java/JavaVirtualMachines/openjdk-17.0.2/Contents/Home
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
mvn clean install


# if there is some changes under common/, please also change
# it's version in pom.xml, and change the reference in other
# pom.xml(s), then
```shell
mvn dependency:purge-local-repository -DmanualInclude="org.termi.common:termi-common"
mvn clean install
```


# Technical
- [Volt - Bootstrap 5 Admin Dashboard](https://github.com/themesberg/volt-bootstrap-5-dashboard)
- [Thymeleaf](https://www.thymeleaf.org/)