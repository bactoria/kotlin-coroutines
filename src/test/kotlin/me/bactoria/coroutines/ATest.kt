package me.bactoria.coroutines

import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ATest : FunSpec({

	test("쓰레드 - 30초 컷") {
		repeat(100_000) {
			thread {
				Thread.sleep(1000)
				print(".")
			}
		}
	}

	test("코루틴 - 3초 컷").config(coroutineTestScope = true) {
		repeat(100_000) {
			launch {
				delay(1000L)
				print(".")
			}
		}
	}
})
