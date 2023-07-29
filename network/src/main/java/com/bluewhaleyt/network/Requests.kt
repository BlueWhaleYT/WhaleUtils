package com.bluewhaleyt.network

/**
 * Enum class representing various HTTP request methods.
 *
 * <p class="note">
 *     Please see <a href="https://www.rfc-editor.org/rfc/rfc9110.html">RFC 9110 - HTTP Semantics</a> for the usages of HTTP request methods.
 * </p>
 */
enum class Requests {
    /**
     * Used with a proxy that can dynamically switch to being a tunnel.
     */
    CONNECT, // TODO

    /**
     * Used to delete a resource identified by a URI.
     */
    DELETE,

    /**
     * Used to retrieve a representation of a resource.
     */
    GET,

    /**
     * Same as GET, but returns only headers and no body.
     */
    HEAD, // TODO

    /**
     * Used to describe the communication options for the target resource.
     */
    OPTIONS, // TODO

    /**
     * Used to apply partial modifications to a resource.
     */
    PATCH,

    /**
     * Used to submit an entity to the specified resource, often causing a change in state or side effects on the server.
     */
    POST,

    /**
     * Used to replace all current representations of the target resource with the uploaded content.
     */
    PUT,

    /**
     * Used to invoke a remote, application-layer loop-back of the request message.
     */
    TRACE, // TODO
}