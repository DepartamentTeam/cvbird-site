import cx from 'classnames';
import styles from '../styles/Signin.module.css'
import { FormEvent } from 'react'

export default Signin;

function Signin() {
  return (
    <>

      <main className={cx(styles["form-signin"],"text-center","mt-5")}>
        <form  method="post" action="/signin">
          <h1 className="h3 mb-3 fw-normal">Please sign in</h1>

          <div className="form-floating">
            <input type="text" name="username" className="form-control" id="floatingInput" placeholder="Username" />
            <label htmlFor="floatingInput">Email address</label>
          </div>
          <div className="form-floating">
            <input type="password" name="password" className="form-control" id="floatingPassword" placeholder="Password" />
            <label htmlFor="floatingPassword">Password</label>
          </div>

          <button className="w-100 btn btn-lg btn-primary" type="submit" value="Submit">Sign in</button>
        </form>
      </main>

    </>
  )
}