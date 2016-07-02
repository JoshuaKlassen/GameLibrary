package jgame.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jgame.util.Vector2F;

public final class ParticleFactory {

	private static final Random random = new Random();
	
	public static List<Particle> generateParticles(Vector2F position, int minParticles, int maxParticles, float minLifespan, float maxLifespan, float minVelocity, float maxVelocity, int size, Color color){
		List<Particle> particles = new ArrayList<Particle>();
		int numberOfParticles = random.nextInt(maxParticles - minParticles) + minParticles;
		
		for(int i = 0; i < numberOfParticles; i ++){
			float lifespan = random.nextFloat() * (maxLifespan - minLifespan) + minLifespan;
			float velocityX = random.nextFloat() * (maxVelocity) - minVelocity;
			float velocityY = random.nextFloat() * (maxVelocity) - minVelocity;
			particles.add(new Particle(position, lifespan, new Vector2F(velocityX, velocityY), color, size));
		}
		return particles;
	}
	
	public static List<Particle> generateParticles(Vector2F position, float rangeFromPosition, int minParticles, int maxParticles, float minLifespan, float maxLifespan, float minVelocity, float maxVelocity, int size, Color color){
		List<Particle> particles = new ArrayList<Particle>();
		int numberOfParticles = random.nextInt(maxParticles - minParticles) + minParticles;
		
		for(int i = 0; i < numberOfParticles; i ++){
			float lifespan = random.nextFloat() * (maxLifespan - minLifespan) + minLifespan;
			float velocityX = random.nextFloat() * (maxVelocity) - minVelocity;
			float velocityY = random.nextFloat() * (maxVelocity) - minVelocity;
			float spawnX = random.nextFloat() * (rangeFromPosition) * (random.nextBoolean() ? 1 : -1);
			float spawnY = random.nextFloat() * (rangeFromPosition) * (random.nextBoolean() ? 1 : -1);
			particles.add(new Particle(Vector2F.add(position, new Vector2F(spawnX, spawnY)), lifespan, new Vector2F(velocityX, velocityY), color, size));
		}
		return particles;
	}
	
}