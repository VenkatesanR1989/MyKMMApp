package com.example.shared

import platform.Foundation.NSUserDefaults
import platform.UIKit.UIViewController

actual typealias Controller = UIViewController

actual fun Controller.getBool(key: String): Boolean {
  return NSUserDefaults.standardUserDefaults.boolForKey(key)
}

actual fun Controller.setBool(key: String, value: Boolean) {
  NSUserDefaults.standardUserDefaults.setBool(value, key)
}