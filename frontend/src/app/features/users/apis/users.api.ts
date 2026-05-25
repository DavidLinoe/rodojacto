import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Collaborator } from '../models/users.model';

@Injectable()
export class CollaboratorsApi {
  constructor(private apiService: ApiService) {}

  getAllCollaborators(companyId: string) {
    return this.apiService.get<Collaborator[]>(`collaborators?companyId=${companyId}`);
  }

  createCollaborator(user: Partial<Collaborator>) {
    return this.apiService.post<Collaborator>('collaborators', user);
  }

  updateCollaborator(id: number, user: Partial<Collaborator>) {
    return this.apiService.put<Collaborator>(`collaborators`, {id,...user});
  }

  deleteCollaborator(id: number) {
    return this.apiService.delete<Collaborator>(`collaborators?userId=${id}`);
  }
}
