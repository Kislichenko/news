export interface User {
  username: string
  password: string
  email?: string
  confirmPassword?: string
  name?: string
  surname?: string
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

export enum AdBlockType{
  Simple = "SIMPLE",
  Big = "BIG",
  Header = "HEADER",
  Footer = "FOOTER"
}


export interface ReqData {
  id?: string
  subject: string
  startDate: Date
  endDate: Date
  legalData: string
  creationDate: Date
  type: Role
  creator: string
}
