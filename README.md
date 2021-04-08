# Documentacion - Desafio Spring

## Listado de articulos y filtros
### GET: /api/v1/articles
Devuelve un listado con todos los productos disponibles 
 
### GET: /api/v1/articles?category=categoryName
Devuelve un listado de los porductos filtrados por categoria

### GET: /api/v1/articles?category=categoryName&freeShipping=true
* Devuelve un listado de los porductos filtrados por la combinacion de dos filtros.
* Los filtros disponibles son:
    * category
    * freeShipping (true / false)
    * brand
    * prestige
    * product
    
### GET: /api/v1/articles?category=categoryName&freeShipping=true&order=0
* Devuelve un listado de productos filtrados ordenado.
* Parametros de ordenamiento:
    * 0 - alfabetico ascendente
    * 1 - alfabetico descendente
    * 2 - mayor a menor precio
    * 3 - menor a mayor precio
    
## Solicitud de compra
### POST: /api/v1/purchase-request
* Envia una solicitud de compra.
* Para enviar una solicitud de compra se debe enviar un objeto con una lista de "articles", cada articulo debe contener un producto con:
    * productId
    * name
    * brand
    * quantity
* Ejemplo: {
    "articles":
            [
                {
                    "productId":1,
                    "name":"Desmalezadora",
                    "brand":"Makita",
                    "quantity":1
                },
                {
                    "productId":4,
                    "name":"Zapatillas Deportivas",
                    "brand":"Nike",
                    "quantity":1
                }
            ]
}


### POST: /api/v1/shopping-cart
* Por cada solicitud de compra enviada, se devuelve el total de todas las solicitudes de compra.
* Es independiente de los clientes.

## Clientes
### POST: /api/v1/add-client
* Permite dar de alta nuevos clientes.
* Los datos necesarios para dar de alta un cliente nuevo son:
    * dni (String)
    * name (String)
    * province (String)

### GET: /api/v1/clients
Devuelve un listado con todos los clientes

### GET: /api/v1/clients?province=provinceName
Devuelve un listado de clientes filtrados por provincia.