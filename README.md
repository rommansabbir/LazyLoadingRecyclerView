[![Release](https://jitpack.io/v/jitpack/android-example.svg)](https://jitpack.io/#rommansabbir/NetworkX)

# ⌛ LazyLoadingRecyclerView ⌛

An easy & lightweight library to implemented Lazy Loading/Infinite Scrolling/Pagination with RecyclerView
## Documentation

### Installation

Step 1. Add the JitPack repository to your build file .

```gradle
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency.

```gradle
    dependencies {
            implementation 'com.github.rommansabbir:LazyLoadingRecyclerView:Tag'
    }
```

---

### Version available

| Releases
| ------------- |
| 1.0.0         |


# Usages

### Step 1:
Attach `SpeedyLayoutManager` as the `LayoutManger` to the recycler view by calling this RecyclerView extension function `attachSpeedyLayoutManager()`

```kotlin
    //Attach the SpeedyLayoutManager to the RecyclerView first
    binding.rv.attachSpeedyLayoutManager(LinearLayoutManager.VERTICAL, false)
```

---

### Step 2:
Create an instance of `LazyLoadingRecyclerView` by calling static method `getInstance()`

```kotlin
    private lateinit var lazyLoadingRecyclerView: LazyLoadingRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        .....
        //Create an instance of LazyLoadingRecyclerView
        lazyLoadingRecyclerView = LazyLoadingRecyclerView.getInstance()
    }
```

---

### Step 3:
Implemented the `LazyLoadingRecyclerView.Listener` to the `Activity/Fragment` or Initialize as Anonymous listener.

```kotlin
    MainActivity : AppCompatActivity(), LazyLoadingRecyclerView.Listener{
        override fun loadMore() {
        // Faking load more data request, infinite scrolling
        }
    }
```

or

```kotlin
    private val listener = object : LazyLoadingRecyclerView.Listener{
        override fun loadMore() {
            // Faking load more data request, infinite scrolling
        }
    }
```
---

### Step 4:
Register the `RecyclerView` with `LazyLoadingRecyclerView.Listener` to `LazyLoadingRecyclerView` by calling this method `registerScrollListener()` when your `Activity/Fragment` is in `onResume` state.

```kotlin
    override fun onResume() {
        super.onResume()
        // IMPORTANT - attach the listener `onResume()` state
        lazyLoadingRecyclerView.registerScrollListener(binding.rv, this/listener)
    }
```

---

### Step 5:
Remove the listener by calling this method `removeListener()` when your `Activity/Fragment` is in `onStop` state

```kotlin
    override fun onStop() {
        super.onStop()
        // IMPORTANT - remove the listener `onStop` state
        lazyLoadingRecyclerView.removeListener()
    }
```

---

### Want to update `SmoothScroller` speed per pixel calculation?
You can update `LinearSmoothScroller`s `calculateSpeedPerPixel` by calling this method `SpeedyLinearLayoutManager.setMillisPerInch()`

````kotlin
    SpeedyLinearLayoutManager.setMillisPerInch(5f)
````

---

### Want to update `LazyLoadingRecyclerView` handler delay time?
You can update `LazyLoadingRecyclerView`s handler delay time by calling this method `LazyLoadingRecyclerView.setHandlerDelayTime()`

````kotlin
    LazyLoadingRecyclerView.setHandlerDelayTime(500)
````

---


### Checkout the sample app for the implementaion in detail

---

### Contact me

[Portfolio](https://www.rommansabbir.com/) | [LinkedIn](https://www.linkedin.com/in/rommansabbir/) | [Twitter](https://www.twitter.com/itzrommansabbir/) | [Facebook](https://www.facebook.com/itzrommansabbir/)

---

### License

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

````html
Copyright (C) 2020 Romman Sabbir

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
