export interface User {
  username: string
  password: string
  // returnSecureToken?: boolean
}

export interface FbAuthResponse {
  idToken: string
  expiresIn: string
}
