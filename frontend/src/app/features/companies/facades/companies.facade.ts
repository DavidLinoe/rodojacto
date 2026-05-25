import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { OrganizationsApi } from '../apis/companies.api';
import { Organization } from '../models/companies.model';
import { TokenService } from '../../../services/token.service';

@Injectable()
export class OrganizationsFacade {
  public companies$: BehaviorSubject<Organization[]> = new BehaviorSubject<Organization[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(
    private companiesApi: OrganizationsApi,
    private tokenService: TokenService,
  ) {}

  getAllOrganizations(): void {
    const userId = this.tokenService.decodeToken()?.userId ?? '';
    this.companiesApi.getAllOrganizations(userId).subscribe((response) => {
      if (response.data) {
        this.companies$.next(response.data);
        this.count$.next(response.count || response.data.length);
      }
    });
  }

  createOrganization(company: Partial<Organization>): void {
    this.companiesApi.createOrganization(company).subscribe(() => {
      this.getAllOrganizations();
    });
  }

  updateOrganization(id: string, company: Partial<Organization>): void {
    this.companiesApi.updateOrganization(id, company).subscribe(() => {
      this.getAllOrganizations();
    });
  }

  deleteOrganization(id: string): void {
    this.companiesApi.deleteOrganization(id).subscribe(() => {
      this.getAllOrganizations();
    });
  }
}
