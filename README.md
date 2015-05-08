# prueba-javassist
Prueba de manipulación de clases a nivel bytecode para inserción de código dinámico

# Parámetros de la VM
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000

Utiliza HotSwap para bajar la clase modificada a la JVM.
Depende de la JVM que corra puede obtenerse el siguiete error:
Exception in thread "HotSwap" java.lang.UnsupportedOperationException: schema change not implemented
