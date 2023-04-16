package module3

import module3.emailService.EmailAddress
import zio.{Has, Task, ULayer, ZIO, ZLayer}
import userService.{User, UserID}
import zio.macros.accessible

package object userDAO {

    /**
     * Реализовать сервис с двумя методами
     *  1. list - список всех пользоватиелей
     *  2. findBy - поиск по User ID
     */

     type UserDAO = Has[UserDAO.Service]

      object  UserDAO{

          trait Service{
            def list(): Task[List[User]]
            def findBy(id: UserID): Task[Option[User]]
          }


        private val dbResponse = List(User(UserID(1), EmailAddress("email1@a.ocom")),
          User(UserID(2), EmailAddress("email2@a.ocom")))


         val live: ULayer[UserDAO] = ZLayer.succeed(
           new Service{
             override def list(): Task[List[User]] = ZIO.succeed(dbResponse)

             override def findBy(id: UserID): Task[Option[User]] = ZIO.succeed(dbResponse.find(x => {x.id == id}))

           }
         )
      }
}
