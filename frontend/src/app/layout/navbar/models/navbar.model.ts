export interface NavbarItem {
  id: string;
  label: string;
  icon: string;
  route: string;
}
export const NAVBAR_ITEMS: NavbarItem[] = [
  {
    id: 'home',
    label: 'Home',
    icon: 'home',
    route: '/home',
  },
  {
    id: 'about',
    label: 'About',
    icon: 'info',
    route: '/about',
  },
  {
    id: 'devices',
    label: 'Devices',
    icon: 'cog',
    route: '/devices',
  },
  {
    id: 'collaborators',
    label: 'Collaborators',
    icon: 'collaborators',
    route: '/collaborators',
  },
  {
    id: 'organizations',
    label: 'Organizations',
    icon: 'building',
    route: '/organizations',
  },
];
