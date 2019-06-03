# TP Integrador Anual - Diseño de Sistemas
¿Qué me pongo?

## Requerimientos Funcionales
 
### Cliente

- Crear usuario (mediante login Social o Google - a definir)
- Crear guardarropas.
- Crear prendas
- Asignando Tipo de Prenda: Sabiendo su Categoría, consistente con Tipo de Prenda y Parte del Cuerpo.
- Asignando Tipo de Tela: consistente con Tipo de Prenda: Con color primario obligatorio, secundario opcional (y diferente al primario)

- Pedir todos los atuendos válidos de todos los guardarropas.

- Pedir un atuendo valido al azar de todos los guardarropas.

- Pedir un atuendo válido al azar de un guardarropa específico.
 
### Requerimientos no Funcionales

- Simpleza en UX para uso residencial
  - Métricas :
    - Cantidad de pantallas para consultar sugerencias de atuendos: 1
    - Tiempo promedio para consultar sugerencias <30 segundos

- UX responsive para uso mobile y desktop browser
  - Métricas:
    - compatibilidad con dispositivos móviles 90%
    - compatibilidad con browsers garantizada: Chrome (versión 73.0.3683.103)

- Disponibilidad del sistema 24hs con una efectividad del 99.5% anual.

- Notificación apropiada a los usuarios para todos los mantenimientos planeados 72 horas antes de llevarse a cabo la interrupción del servicio.

- Soporte de 30 Usuarios concurrentes
  - Métrica:
    - Tiempos de respuesta dentro de los valores propuestos

# Version 0.1:

- Creado de Users.
- Creado de Guardarropas para esos Users.
- Creado de Prendas para los guardarropas de esos users.
