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
  styleUrls: ['./profile.css'],
})
export class Profile implements OnInit {
  profileForm!: FormGroup;
  profileImagePreview: string = '/image.png';
  userData!: UserProfile;
  isModalOpen = false;
  selectedModalImageFile: File | null = null;
  modalImagePreview: string | null = null;
  selectedImageFile: File | null = null;

  constructor(private fb: FormBuilder, private adminProfileService: AdminProfileService) {}
  ngOnInit(): void {
    const userEmail = localStorage.getItem('email');
    if (!userEmail) {
      alert('User email not found. Please log in again.');
      return;
    }

    // Fetch user data from backend
    this.adminProfileService.getProfile().subscribe({
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
      },
    });

    this.adminProfileService.getProfilePicture().subscribe({
      next: (response) => {
        if (response && response.profileUrl) {
          this.profileImagePreview = response.profileUrl;
          console.log('Profile image loaded successfully:', response.profileUrl);
        } else {
          console.warn('No profile image found for user.');
        }
      },
      error: (err) => {
        console.error('Error fetching profile image:', err);
      },
    });
  }

  initializeForm(): void {
    this.profileForm = this.fb.group({
      id: [{ value: this.userData.id, disabled: true }],
      firstName: [this.userData.firstName, Validators.required],
      lastName: [this.userData.lastName, Validators.required],
      email: [
        { value: this.userData.email, disabled: true },
        [Validators.required, Validators.email],
      ],
      phoneNumber: [this.userData.phoneNumber],
      dateOfBirth: [this.userData.dateOfBirth],
      roleName: [{ value: this.userData.roleName, disabled: true }],
      profileImage: [null],
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

removeProfilePic(): void {
  const formData = new FormData();

  this.adminProfileService.updateProfilePic(formData).subscribe({
    next: (response) => {
      // Update the preview with the default profile URL returned by backend
      if (response.profileUrl) {
        this.profileImagePreview = response.profileUrl;
      } else {
        // fallback to your placeholder
        this.profileImagePreview = '/image.png';
      }
      alert('Profile picture removed successfully!');
    },
    error: (err) => {
      alert('Failed to remove profile picture!');
      console.error(err);
    }
  });
}


  onSubmit(): void {
    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      return;
    }

    const formValue = this.profileForm.getRawValue();
    const updatePayload = {
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      phoneNumber: formValue.phoneNumber ?? '',
      dateOfBirth: formValue.dateOfBirth ?? '',
    };

    this.adminProfileService.updateUserProfile(updatePayload).subscribe({
      next: () => alert('Profile updated successfully!'),
      error: (err) => {
        alert('Failed to update profile!');
        console.error(err);
      },
    });
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

    const formData = new FormData();
    formData.append('profileImage', this.selectedModalImageFile);

    this.adminProfileService.updateProfilePic(formData).subscribe({
      next: (response) => {
        if (response.profileUrl) {
          this.profileImagePreview = response.profileUrl;
          window.location.reload(); // update preview with backend URL
        } else {
          this.profileImagePreview = this.modalImagePreview!; // fallback local preview
        }
        this.closeModal();
        alert('Profile picture updated successfully!');
      },
      error: (err) => {
        alert('Failed to update profile picture!');
        console.error(err);
      },
    });
  }
}
