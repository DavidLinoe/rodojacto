import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthApi } from '../../apis/auth.api';
import { AuthFacade } from '../../facades/auth.facades';

@Component({
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  selector: 'feature-sign-up',
  templateUrl: './sign-up.component.html',
  providers: [AuthFacade, AuthApi],
})
export class SignUpComponent implements OnInit {
  public signUpForm!: FormGroup;
  public errorMessage: string | null = null;
  public loading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authFacade: AuthFacade,
  ) {}

  ngOnInit(): void {
    this.signUpForm = this.formBuilder.group(
      {
        fullName: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
        corporateName: ['', [Validators.required]],
        registrationCode: ['', [Validators.required]],
      },
      { validators: this.matchPasswords },
    );
  }

  private matchPasswords(group: FormGroup) {
    const password = group.get('password')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return password === confirm ? null : { passwordMismatch: true };
  }

  signUp(): void {
    if (this.signUpForm.invalid) {
      this.signUpForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = null;
    const { confirmPassword, ...payload } = this.signUpForm.value;

    this.authFacade.signUp(payload).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/organizations']);
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err?.error?.message ?? 'Não foi possível criar a conta';
      },
    });
  }
}
