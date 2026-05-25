import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Collaborator } from '../models/collaborators.model';

@Injectable()
export class CollaboratorsApi {
  constructor(private apiService: ApiService) {}

  getAllCollaborators(organizationId: string) {
    return this.apiService.get<Collaborator[]>(`collaborators?organizationId=${organizationId}`);
  }

  createCollaborator(collaborator: Partial<Collaborator>) {
    return this.apiService.post<Collaborator>('collaborators', collaborator);
  }

  updateCollaborator(id: number, collaborator: Partial<Collaborator>) {
    return this.apiService.put<Collaborator>(`collaborators`, {id,...collaborator});
  }

  deleteCollaborator(id: number) {
    return this.apiService.delete<Collaborator>(`collaborators?collaboratorId=${id}`);
  }
}
