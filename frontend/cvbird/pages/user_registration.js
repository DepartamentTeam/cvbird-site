import cx from 'classnames';
import styles from '../styles/Signin.module.css'
import { FormEvent } from 'react'

export default function User_Registration(){
    return(
        <>
          <main className={cx(styles["form-signin"],"text-center","mt-5")}>

            <form action="/user/registration" method="POST" enctype="utf8">
              <h1 className="h3 mb-3 fw-normal">Registration</h1>
                <div>
                    <label >Email address</label>
                     <input type="text" name="email" className="form-control" id="floatingInput" placeholder="Username" />
                </div>
               <div className="form-floating">
                           <input type="password" name="password" className="form-control" id="floatingPassword" placeholder="Password" />
                           <label htmlFor="floatingPassword">Password</label>
                         </div>
                <div className="form-floating">
                            <input type="password" name="confirmPassword" className="form-control" id="floatingPassword" placeholder="Password" />
                            <label htmlFor="floatingPassword">Confirm password</label>
                </div>

                 <button className="w-100 btn btn-lg btn-primary" type="submit" value="Submit">Submit</button>
            </form>
          </main>
        </>
    )
}