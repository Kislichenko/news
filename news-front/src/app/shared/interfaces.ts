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
  Reporter = "ROLE_REPORTER"
}

export enum ReportageStatus{
  Created ="Created",
  Inwork = "Inwork",
  Fixing = "Fixing",
  Closed = "Closed"
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
  wishes: string
  payed: boolean
  confirm: boolean
  contract: boolean
  signature: boolean
  cost: number
}

export interface News {
  id?: string
  article: string
  body: string
  bad: boolean
  realization: boolean
  infoManager?: string
}

export interface Reportage {
  id?: string
  infoManager: string
  reporter: string
  startDate: Date
  endDate: Date
  text: string
  comment: string
  confirm: boolean
  status: ReportageStatus
  news: string
}
