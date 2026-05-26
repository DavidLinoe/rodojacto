import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { AuthCollaborator, SignUpPayload } from '../models/auth.model';

@Injectable()
export class AuthApi {
  constructor(private apiService: ApiService) {}

  signIn(email: string, password: string) {
    return this.apiService.post<AuthCollaborator>(
      'auth/login',
      { email, password } as unknown as Partial<AuthCollaborator>,
    );
  }

  signUp(payload: SignUpPayload) {
    return this.apiService.post<AuthCollaborator>(
      'auth/register',
      payload as unknown as Partial<AuthCollaborator>,
    );
  }
}
