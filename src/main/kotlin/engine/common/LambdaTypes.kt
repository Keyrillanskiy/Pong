package engine.common

typealias Action = () -> Unit

typealias Consumer<T> = (T) -> Unit

typealias Mapper<T> = (T) -> T