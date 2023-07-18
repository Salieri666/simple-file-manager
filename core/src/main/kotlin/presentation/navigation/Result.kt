package presentation.navigation

interface IEmptyResult

interface IResult<out T> : IEmptyResult {
    val data: T
}