
### [Download APK](https://drive.google.com/file/d/1I6MMBrUmwhG-avq5Km_WfQWnGaZdz5pD/view?usp=sharing)
### [Structure is Based on my own structure here](https://github.com/EsmaeelNabil/refresh-starter-template/tree/hilt)


A            |  A
:-------------------------:|:-------------------------:
![](./art/permissions-screen.png)  |  ![](./art/permissions-screen2.png)
![](./art/location-screen.png)  |  ![](./art/empty-screen.png)
![](./art/list-screen.png)  |  ![](./art/list-screen.png)

#### requirements

1. Show a list of Currency Wallets (Icon, Symbol, balance). 
   As you can see there is a lot of code reuse, try to improve it 
   Cryptocoin and Metal are considered as Asset, Fiat and Asset are considered as Currency. 
   Each Currency has a Wallet, and you can have multiple Wallets per Currency.

  a. for Metals show the name in the list eg.: "Gold" 
  b. do not show deleted wallets
  c. Data should only be retrieved from the Repository

2. Sort the list by type : fiat, cryptocoins, metals and the balance of the wallet
3. Add a functionality to filter Currencies by type (a simple button which rotates the type is enough)
4. If you click on an entry open a simple DetailView where you show the price of the coin
   a. use precision to format the price with correct amount of decimal places
   b. Prices are euro prices

5. Test your implementation with Unit Tests


GENERAL

* You are free to refactor and improve the given structure. let us know what and why
* Use an architecture pattern of your choice

NICE TO HAVE 
* You are free to implement also a nice UI 

--------------------------------------------------
Cryptocoin and Metal are considered as Asset             
Fiat and Asset are considered as Currency               
Each Currency has a Wallet
and you can have multiple Wallets per Currency
--------------------------------------------------

Show a list of Currency Wallets (Icon, Symbol, balance)
for Metals show the name in the list eg.: "Gold" 
do not show deleted wallets



### Features
* [Hiding secretKeys in properties Files using gradle secrets](https://github.com/google/secrets-gradle-plugin)
* [Dependencies Versions & update plugin](https://jmfayard.github.io/refreshVersions/)
* [Dagger-Hilt](https://dagger.dev/hilt/gradle-setup)
* [Usecasesapproach](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576)
* [Dependecy inversion](https://medium.com/@kedren.villena/simplifying-dependency-inversion-principle-dip-59228122649a)
 and [SOILID](https://medium.com/backticks-tildes/the-s-o-l-i-d-principles-in-pictures-b34ce2f1e898) 


---
###### to add new `Dependencies` you have two methods :
- do it like this [Video](https://youtu.be/VhYERonB8co)
- or add the dependency in `buildSrc/Libs` like this
```const val security_crypto: String = "androidx.security:security-crypto:_"```
- then add it in `build.gradle.kts` of the app  like this
```implementation(Libs.State.appState)```
###### to get latest `Dependencies` versions run this gradle command :
```
./gradlew refreshVersions
```
##### latest versions will be in [versions.properties]() to select from.

###### `TO ADD NEW SECRETS ` put them in `secrets.properties` file that is located in `ROOT` project folder

``` json
token_header_name="Authorization"
base_url="https://api.github.com/"
```

























