import { FormGroup } from '@angular/forms';

export function isFormEmpty(form: FormGroup): boolean {
  return Object.values(form.value).every(v => v == null || v === '');
}
