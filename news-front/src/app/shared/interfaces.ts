export interface User {
  username: string
  password: string
  // returnSecureToken?: boolean
}

export interface FbAuthResponse {
  idToken: string
  expiresIn: string
}

export enum Role{
  Admin = "ROLE_ADMIN",
  User = "ROLE_USER",
  AdManager = "ROLE_AD_MANAGER",
  InfoManager = "ROLE_INFO_MANAGER",
  Repoter = "REPORTER"
}
