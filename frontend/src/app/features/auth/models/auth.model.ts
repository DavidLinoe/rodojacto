export interface Auth {
  email: string;
  password: string;
}

export interface SignUpPayload {
  name: string;
  email: string;
  password: string;
  role?: string;
  organizationId?: string;
}

export interface AuthCollaborator {
  id: string;
  name: string;
  email: string;
  role: string;
  organizationId: string;
  createdAt: string;
  token: string;
}
