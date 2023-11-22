package me.bactoria.coroutines

import io.kotest.core.spec.style.FunSpec
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BTest : FunSpec({

	test("절대 끝나지 않는 테스트").config(coroutineTestScope = true) {
		println("Before")
		suspendCoroutine<Unit> { continuation ->
			println("Before2")
		} // <- 일지중단 시켰지만, 재개를 하지 않아서 돌아오지 않는다.
		println("After")
	}

	test("끝나는 테스트").config(coroutineTestScope = true) {
		println("Before")
		suspendCoroutine { continuation ->
			println("Before2")
			continuation.resumeWith(Result.success(Unit)) // <- 재개 해주세요.
		}
		println("After")
	}
	
	test("커스텀 delay 함수 만들기").config(coroutineTestScope = true) {
		println("Before")
		myDelay(1000L)
		println("After")
	}
})

private val executor = Executors.newSingleThreadScheduledExecutor{
	Thread(it, "scheduler").apply { isDaemon = true }
}

suspend fun myDelay(timeMillis: Long) {
	suspendCoroutine { continuation ->
		executor.schedule({
			continuation.resume(Unit)
		}, timeMillis, TimeUnit.MILLISECONDS)
	}
}
