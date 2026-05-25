import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Organization } from '../models/companies.model';

@Injectable()
export class OrganizationsApi {
  constructor(private apiService: ApiService) {}

  getAllOrganizations(userId: string) {
    return this.apiService.get<Organization[]>(`organizations?id=${userId}`);
  }

  createOrganization(company: Partial<Organization>) {
    return this.apiService.post<Organization>('organizations', company);
  }

  updateOrganization(id: string, company: Partial<Organization>) {
    return this.apiService.put<Organization>('organizations', { id, ...company });
  }

  deleteOrganization(id: string) {
    return this.apiService.delete<Organization>(`organizations?id=${id}`);
  }
}
