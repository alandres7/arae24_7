export const PUNTOS_RUTAS: any[] = [
  {
    lat: 6.267649,
    long: -75.566555,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/parking_bicycle.png'
  },
  {
    lat: 6.264366,
    long: -75.563584,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/train.png'
  },
  {
    lat: 6.264138,
    long: -75.568585,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/tramway.png'
  },
  {
    lat: 6.266167,
    long: -75.562308,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/busstop.png'
  }
];

export const PUNTOS_RUTAS_LINEAS: any[] = [
  {
    lat: 6.290831,
    long: -75.558979,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/busstop.png'
  },
  {
    lat: 6.255197,
    long: -75.602245,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/train.png'
  },
  {
    lat: 6.241462,
    long: -75.553314,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/tramway.png'
  },
  {
    lat: 6.233220,
    long: -75.580787,
    contenidoInfo: "<b>test</b>",
    icon: '..assets/movilidad/busstop.png'
  }
];


export const PUNTOS_DETALLES_RUTAS: any[] =[
  [
    {
      nombre: '225 1',
      empresa: 'TransCastilla',
      horario: 'L-D 4:00am- 10:45pm',
      frecuencia: 'Cada 15 min',
      tarifa: '$2000',
      checked: false,
      flightPath: null,
      puntos: [
        { lat: 6.306689, lng: -75.558172 },
        { lat: 6.324064, lng: -75.595084 }
      ],
      tipo: "Carro"
    },
    {
      nombre: '225 2',
      empresa: 'TransCastilla',
      horario: 'L-D 4:00am- 10:45pm',
      frecuencia: 'Cada 15 min',
      tarifa: '$2000',
      checked: false,
      flightPath: null,
      puntos: [
        { lat: 6.306689, lng: -75.558172 },
        { lat: 6.321064, lng: -75.576084 }
      ],
      tipo: "Carro"
    }
  ],
  [
    {
      nombre: 'Linea A Metro',
      horario: 'L-D 4:00am- 10:45pm',
      frecuencia: 'Cada 5 min',
      tarifa: '$2000',
      checked: false,
      flightPath: null,
      puntos: [
        { lat: 6.276146, lng: -75.570149 },
        { lat: 6.284809, lng: -75.563722 },
        { lat: 6.292517, lng: -75.578627 }
      ],
      tipo: "Metro"
    },
    {
      nombre: 'Linea B Metro',
      horario: 'L-D 4:00am- 10:45pm',
      frecuencia: 'Cada 5 min',
      tarifa: '$2000',
      checked: false,
      flightPath: null,
      puntos: [
        { lat: 6.276146, lng: -75.570149 },
        { lat: 6.263709, lng: -75.554722 },
        { lat: 6.287217, lng: -75.544087 }
      ],
      tipo: "Metro"
    },
    {
      nombre: 'Linea C Metro',
      horario: 'L-D 4:00am- 10:45pm',
      frecuencia: 'Cada 5 min',
      tarifa: '$2000',
      checked: false,
      flightPath: null,
      puntos: [
        { lat: 6.276146, lng: -75.570149 },
        { lat: 6.286709, lng: -75.558722 },
        { lat: 6.293217, lng: -75.554087 }
      ],
      tipo: "Metro"
    }
  ],
  [
    {
      nombre: 'CV34',
      descripcion: 'Ciclovia Palace-Av Bolivariana',
      checked: false,
      flightPath: null,
      puntos: [
        { lat: 6.273005, lng: -75.564693 },
        { lat: 6.276146, lng: -75.570149 }
      ],
      tipo: "Encicla"
    },
    {
      nombre: 'CV34 2',
      descripcion: 'Ciclovia Palace-Av Bolivariana',
      checked: false,
      flightPath: null,
      puntos: [
        { lat: 6.223005, lng: -75.584693 },
        { lat: 6.236146, lng: -75.590149 }
      ],
      tipo: "Encicla"
    }
  ]
];
