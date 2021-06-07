package com.example.shared

expect class Image

expect fun ByteArray.toNativeImage(): Image?
