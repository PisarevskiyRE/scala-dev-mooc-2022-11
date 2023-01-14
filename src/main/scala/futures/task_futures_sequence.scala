package futures

import scala.concurrent.{ExecutionContext, Future}

object task_futures_sequence {

  /**
   * В данном задании Вам предлагается реализовать функцию fullSequence,
   * похожую на Future.sequence, но в отличии от нее,
   * возвращающую все успешные и не успешные результаты.
   * Возвращаемое тип функции - кортеж из двух списков,
   * в левом хранятся результаты успешных выполнений,
   * в правово результаты неуспешных выполнений.
   * Не допускается использование методов объекта Await и мутабельных переменных var
   */
  /**
   * @param futures список асинхронных задач
   * @return асинхронную задачу с кортежом из двух списков
   */
  def fullSequence[A](futures: List[Future[A]])
                     (implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {


    futures.foldLeft[Future[(List[A], List[Throwable])]](Future.successful(List.empty[A], List.empty[Throwable])) {
      case (acc, future) => acc.flatMap {
        case (successList, failureList) => future.map(value => (successList :+ value, failureList)).recoverWith{
          case ex => Future.successful((successList,  failureList :+ ex))
        }
      }
    }
  }

}