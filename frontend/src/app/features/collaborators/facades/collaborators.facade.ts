import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CollaboratorsApi } from '../apis/users.api';
import { Collaborator } from '../models/users.model';
import { TokenService } from '../../../services/token.service';

@Injectable()
export class CollaboratorsFacade {
  public users$: BehaviorSubject<Collaborator[]> = new BehaviorSubject<Collaborator[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(private usersApi: CollaboratorsApi, private tokenService: TokenService) {}

  getAllCollaborators(): void {
    const token = this.tokenService.decodeToken();
    if (!token?.companyId) {
      console.error('Organization ID is missing in the token.');
      return;
    }
    this.usersApi.getAllCollaborators(token?.companyId).subscribe((response) => {
      if (response.data) {
        this.users$.next(response.data);
        this.count$.next(response.count || response.data.length);
      }
    });
  }

  createCollaborator(user: Partial<Collaborator>): void {
    this.usersApi.createCollaborator(user).subscribe(() => {
      this.getAllCollaborators();
    });
  }

  updateCollaborator(id: number, user: Partial<Collaborator>): void {
    this.usersApi.updateCollaborator(id, user).subscribe(() => {
      this.getAllCollaborators();
    });
  }

  deleteCollaborator(id: number): void {
    this.usersApi.deleteCollaborator(id).subscribe(() => {
      this.getAllCollaborators();
    });
  }
}
