import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminProfileService } from '../../services/admin-profile-service'; // adjust path as needed

interface UserProfile {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  dateOfBirth: string;
  roleName: string;
  profileImageUrl?: string;
}

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.html',
  styleUrls: ['./profile.css']
})
export class Profile implements OnInit {

  profileForm!: FormGroup;
  profileImagePreview: string = 'https://via.placeholder.com/150';
  userData!: UserProfile;
  isModalOpen = false;
  selectedModalImageFile: File | null = null;
  modalImagePreview: string | null = null;
  selectedImageFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private adminProfileService: AdminProfileService
  ) {}
  ngOnInit(): void {
    const userEmail = localStorage.getItem('email'); 
    if (!userEmail) {
      alert('User email not found. Please log in again.');
      return;
    }

    // Fetch user data from backend
    this.adminProfileService.getProfile(userEmail).subscribe({
      next: (data) => {
        this.userData = data;
        this.initializeForm();
        if (this.userData.profileImageUrl) {
          this.profileImagePreview = this.userData.profileImageUrl;
        }
      },
      error: (err) => {
        console.error('Error fetching user profile:', err);
        alert('Failed to load profile data.');
      }
    });

    this.adminProfileService.getProfilePicture()
  }

  initializeForm(): void {
    this.profileForm = this.fb.group({
      id: [{ value: this.userData.id, disabled: true }],
      firstName: [this.userData.firstName, Validators.required],
      lastName: [this.userData.lastName, Validators.required],
      email: [{ value: this.userData.email, disabled: true }, [Validators.required, Validators.email]],
      phoneNumber: [this.userData.phoneNumber],
      dateOfBirth: [this.userData.dateOfBirth],
      roleName: [{ value: this.userData.roleName, disabled: true }],
      profileImage: [null]
    });
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedImageFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.profileImagePreview = reader.result as string;
      };
      reader.readAsDataURL(this.selectedImageFile);
    }
  }

  updateProfilePic(): void {
    if (this.selectedImageFile) {
      alert('Profile picture updated (simulate)');
      // TODO: this.adminProfileService.updateProfilePic(this.selectedImageFile).subscribe(...)
    } else {
      alert('Please select an image first');
    }
  }

  removeProfilePic(): void {
    this.profileImagePreview = 'https://via.placeholder.com/150';
    alert('Profile picture removed (simulate)');
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      return;
    }

    const formData = new FormData();
    const formValue = this.profileForm.getRawValue();
    formData.append('firstName', formValue.firstName);
    formData.append('lastName', formValue.lastName);
    formData.append('phoneNumber', formValue.phoneNumber ?? '');
    formData.append('dateOfBirth', formValue.dateOfBirth ?? '');

    if (formValue.profileImage) {
      formData.append('profileImage', formValue.profileImage);
    }

    console.log('Form Data ready to send', formData);
    alert('Profile updated successfully!');
  }

  openModal() {
    this.isModalOpen = true;
    this.selectedModalImageFile = null;
    this.modalImagePreview = null;
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedModalImageFile = null;
    this.modalImagePreview = null;
  }

  onModalImageSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedModalImageFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.modalImagePreview = reader.result as string;
      };
      reader.readAsDataURL(this.selectedModalImageFile);
    }
  }

  confirmUpdateProfilePic() {
    if (!this.selectedModalImageFile) return;
    this.profileImagePreview = this.modalImagePreview!;
    this.closeModal();
    alert('Profile picture updated successfully!');
  }
}
