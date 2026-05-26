export interface Auth {
  email: string;
  password: string;
}

export interface SignUpPayload {
  fullName: string;
  email: string;
  password: string;
  corporateName: string;
  registrationCode: string;
}

export interface AuthCollaborator {
  token: string;
  type?: string;
}
