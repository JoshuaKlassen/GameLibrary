package jgame.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jgame.util.Vector2;

public final class ParticleFactory {

	private static final Random random = new Random();
	
	public static List<Particle> generateParticles(Vector2 position, int minParticles, int maxParticles, float minLifespan, float maxLifespan, float minVelocity, float maxVelocity, int size, Color color){
		List<Particle> particles = new ArrayList<Particle>();
		int numberOfParticles = random.nextInt(maxParticles - minParticles) + minParticles;
		
		for(int i = 0; i < numberOfParticles; i ++){
			float lifespan = random.nextFloat() * (maxLifespan - minLifespan) + minLifespan;
			float velocityX = random.nextFloat() * ((maxVelocity) - minVelocity) * (random.nextBoolean() ? 1 : -1);
			float velocityY = random.nextFloat() * ((maxVelocity) - minVelocity) * (random.nextBoolean() ? 1 : -1);
			particles.add(new Particle(position, lifespan, new Vector2(velocityX, velocityY), color, size));
		}
		return particles;
	}
	
	public static List<Particle> generateParticles(Vector2 position, float rangeFromPosition, int minParticles, int maxParticles, float minLifespan, float maxLifespan, float minVelocity, float maxVelocity, int size, Color color){
		List<Particle> particles = new ArrayList<Particle>();
		int numberOfParticles = random.nextInt(maxParticles - minParticles) + minParticles;
		
		for(int i = 0; i < numberOfParticles; i ++){
			float lifespan = random.nextFloat() * (maxLifespan - minLifespan) + minLifespan;
			float velocityX = random.nextFloat() * (maxVelocity) - minVelocity;
			float velocityY = random.nextFloat() * (maxVelocity) - minVelocity;
			float spawnX = random.nextFloat() * (rangeFromPosition) * (random.nextBoolean() ? 1 : -1);
			float spawnY = random.nextFloat() * (rangeFromPosition) * (random.nextBoolean() ? 1 : -1);
			particles.add(new Particle(Vector2.add(position, new Vector2(spawnX, spawnY)), lifespan, new Vector2(velocityX, velocityY), color, size));
		}
		return particles;
	}
	
}