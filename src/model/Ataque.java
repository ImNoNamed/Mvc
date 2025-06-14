package model;

public class Ataque {
    private String damagename;
    private String damagetype;
    private int damagepotency;

    public Ataque(String damagename, String damagetype, int damagepotency) {
        this.damagename = damagename;
        this.damagetype = damagetype;
        this.damagepotency = damagepotency;
    }

    // Getters
    public String getDamageName() {
        return damagename;
    }

    public String getDamageType() {
        return damagetype;
    }

    public int getDamagePotency() {
        return damagepotency;
    }

    public int getPower() {
        return damagepotency;  // Ahora sí devuelve la potencia real
    }

    // Setters
    public void setDamageName(String damagename) {
        this.damagename = damagename;
    }

    public void setDamageType(String damagetype) {
        this.damagetype = damagetype;
    }

    public void setDamagePotency(int damagepotency) {
        this.damagepotency = damagepotency;
    }

    // Aplica el ataque al Pokémon enemigo
    public void applyAttack(Pokemon enemy) {
        int damage = calculateDamage(enemy.getType(), enemy.getDefense());
        enemy.subtractHp(damage);
        System.out.println("El ataque " + damagename + " ha hecho " + damage + " de daño a " + enemy.getName());
    }

    // Calcula el daño con o sin ventaja de tipo
    public int calculateDamage(String enemyType, int enemyDefense) {
        double baseMultiplier = 1.0;

        // Verificar ventaja de tipo
        if (advantage(enemyType)) {
            baseMultiplier += 0.3; // Ventaja de tipo
        }

        // Calcular daño bruto
        int rawDamage = (int) (damagepotency * baseMultiplier);

        // Reducir daño por la defensa del enemigo
        int finalDamage = rawDamage - enemyDefense;

        // Asegurar que el daño no sea negativo
        return Math.max(finalDamage, 1); // El daño mínimo será 1
    }

    // Verifica ventaja de tipo
    public boolean advantage(String enemytype) {
        return (damagetype.equals("agua") && enemytype.equals("fuego")) ||
               (damagetype.equals("fuego") && enemytype.equals("planta")) ||
               (damagetype.equals("planta") && enemytype.equals("agua"));
    }

    public static class AtaqueNoDisponibleException extends Exception {
        public AtaqueNoDisponibleException(String mensaje) {
            super(mensaje);
        }
    }
}

