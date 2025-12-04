import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'mask',
  standalone: true
})
export class MaskPipe implements PipeTransform {

  transform(value: string, tipo: string): string {
    if (!value) return '';

    value = value.replace(/\D/g, '');

    if (tipo === 'telefone') {
      if (value.length === 10) {
        return value.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
      }
      return value.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    }

    if (tipo === 'cpf') {
      return value.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
    }

    return value;
  }
}

