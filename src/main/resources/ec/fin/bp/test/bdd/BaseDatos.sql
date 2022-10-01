--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2022-10-01 14:46:10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 22849)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    contrasenia character varying(255),
    estado boolean,
    id_cliente integer NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 20829)
-- Name: cliente_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_cliente_seq OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 22827)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta (
    id_cuenta integer NOT NULL,
    estado boolean,
    numero_cuenta character varying(255),
    saldo_inicial numeric(19,2),
    tipo character varying(255),
    id_cliente integer NOT NULL
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 22846)
-- Name: cuenta_id_cuenta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuenta_id_cuenta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cuenta_id_cuenta_seq OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 22834)
-- Name: movimiento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movimiento (
    id_movimiento integer NOT NULL,
    fecha date,
    saldo numeric(19,2),
    tipo character varying(255),
    valor numeric(19,2),
    id_cuenta integer NOT NULL
);


ALTER TABLE public.movimiento OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 22847)
-- Name: movimiento_id_movimiento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movimiento_id_movimiento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movimiento_id_movimiento_seq OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 22839)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    id_persona integer NOT NULL,
    direccion character varying(255),
    edad integer,
    genero character varying(255),
    identificacion character varying(255),
    nombre character varying(255),
    telefono character varying(255)
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 22848)
-- Name: persona_id_persona_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persona_id_persona_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.persona_id_persona_seq OWNER TO postgres;

--
-- TOC entry 3186 (class 2606 OID 22853)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 3180 (class 2606 OID 22833)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (id_cuenta);


--
-- TOC entry 3182 (class 2606 OID 22838)
-- Name: movimiento movimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT movimiento_pkey PRIMARY KEY (id_movimiento);


--
-- TOC entry 3184 (class 2606 OID 22845)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id_persona);


--
-- TOC entry 3188 (class 2606 OID 22859)
-- Name: movimiento fk8veysyanipny5mpudj13t8873; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT fk8veysyanipny5mpudj13t8873 FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(id_cuenta);


--
-- TOC entry 3189 (class 2606 OID 22864)
-- Name: cliente fkb53lbx2pbv9hoqqn5fdawu602; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT fkb53lbx2pbv9hoqqn5fdawu602 FOREIGN KEY (id_cliente) REFERENCES public.persona(id_persona);


--
-- TOC entry 3187 (class 2606 OID 22854)
-- Name: cuenta fkmkmi3xf6wrp0y1mdn8nm4weim; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT fkmkmi3xf6wrp0y1mdn8nm4weim FOREIGN KEY (id_cliente) REFERENCES public.cliente(id_cliente);


-- Completed on 2022-10-01 14:46:10

--
-- PostgreSQL database dump complete
--

