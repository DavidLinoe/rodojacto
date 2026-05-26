import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { OrganizationsApi } from '../apis/organizations.api';
import { Organization } from '../models/organizations.model';

@Injectable()
export class OrganizationsFacade {
  public organizations$: BehaviorSubject<Organization[]> = new BehaviorSubject<Organization[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(private organizationsApi: OrganizationsApi) {}

  getAllOrganizations(): void {
    this.organizationsApi.getAllOrganizations().subscribe((response) => {
      if (response.data) {
        this.organizations$.next(response.data);
        this.count$.next(response.count ?? response.data.length);
      }
    });
  }

  createOrganization(organization: Partial<Organization>): void {
    this.organizationsApi.createOrganization(organization).subscribe((response) => {
      if (response.data) {
        const updated = [...this.organizations$.getValue(), response.data];
        this.organizations$.next(updated);
        this.count$.next(updated.length);
      }
    });
  }

  updateOrganization(id: number, organization: Partial<Organization>): void {
    this.organizationsApi.updateOrganization(id, organization).subscribe((response) => {
      if (response.data) {
        const updated = this.organizations$
          .getValue()
          .map((item) => (item.id === id ? response.data! : item));
        this.organizations$.next(updated);
      }
    });
  }

  deleteOrganization(id: number): void {
    this.organizationsApi.deleteOrganization(id).subscribe(() => {
      const updated = this.organizations$.getValue().filter((item) => item.id !== id);
      this.organizations$.next(updated);
      this.count$.next(updated.length);
    });
  }
}
