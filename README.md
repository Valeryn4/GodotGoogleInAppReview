# godot-google-in-app-review
Godot Android plugin for the Google In App Review

[https://developer.android.com/guide/playcore/in-app-review](https://developer.android.com/guide/playcore/in-app-review)

![IMAGE EXAMPLE](https://developer.android.com/images/google/play/in-app-review/iar-flow.jpg)

## Usage & Docs

You can find the docs for this first-party plugin in the [official Godot docs](https://docs.godotengine.org/en/stable/tutorials/platform/android_in_app_purchases.html).


## Compiling

Prerequisites:

- Android SDK (platform version 29)
- the Godot Android library (`godot-lib.***.release.aar`) for your version of Godot from the [downloads page](https://godotengine.org/download).

Steps to build:

1. Clone this Git repository
2. Put `godot-lib.***.release.aar` in `./godot-google-in-app-review/libs/`
3. Run `./gradlew build` in the cloned repository

If the build succeeds, you can find the resulting `.aar` files in `./godot-google-in-app-review/build/outputs/aar/`.

## Usage

```gdscript

var google_in_app_review = null

func _ready() -> void :
    google_in_app_review = Engine.get_singleton("GodotGoogleInAppReview")
    google_in_app_review.connect("result", self, "_on_result") #signal result review

func request_review() -> void :
    google_in_app_review.requestReview() #request review dialog

func request_review_fake() -> void :
    google_in_app_review.requestFakeReview() #request fake from FakeReviewManager


func _on_result(res : bool, text : String) -> void :
    print("Google request review result: ", res, " info:", text)


```