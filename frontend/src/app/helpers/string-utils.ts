export const Initials = (fullName: string): string => {
  return (fullName || '')
    .split(' ')
    .map(word => word.charAt(0).toUpperCase())
    .join('');
}
