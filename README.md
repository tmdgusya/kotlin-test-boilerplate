# KotlinTestBoilerplate

Welcome! This Plugin reduce your time that waste by write test code.
We provide some kind of patterns that is you often used

> You must install kotest and mockk library in your project, Currently, we support only kotest and mockk

**Currently, This Plugin is beta version!**

## Overview

It's very convenient tool. it can create test boilerplate.
It's easily accessible, we provide shortcut keymap `cmd + shift + t` that you used to create test classes.

### Easy to create unit test

Generally, we mock every property that be injected by other when we create unit-test.

![example01](https://user-images.githubusercontent.com/57784077/188525568-0f445024-bbb6-45a0-9d13-fb3e2c371789.gif)


## Features

- Unit Test Boiler Plate

## Contributes

Unfortunately, you can't contribute at this time. This Plugin need time to stabilize.
But, you can contribute to some pattern that used to create some test code
We want to be in a state where we can get other people's contributions as soon as possible.

## Report TestBoilerPlate pattern

If you want to report some pattern that you are frequently used when creating a test class, then Follow the process below

1. **[Issue] - [New Issue]**
2. **Write title ("My Unit Test Boiler Plate Pattern ...")**
3. **Write sudo code by kotlin styles.**
```kotlin

// This is not test class
@Service
class OrderService(private val repository: OrderRepsoitory) {
    
    fun order() {
        // doSome ...
    }
    
}

// This is test class that be made by kotlin-boilerplate plugins
class OrderServiceTestClass : FunSpec({
    
    val mockRepository: OrderRepsoitory = mock<>(relaxed = true)
    // SUT
    val orderService = OrderService(mockRepository)
})
```