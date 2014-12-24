package net.swordsofvalor.rpgplus.util.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public final class ChunkIterator implements Iterator<Chunk> {
	
	private List<Chunk> chunkList;
	
	public ChunkIterator(List<Chunk> chunkList) {
		this.chunkList = chunkList;
	}
	
	@Override
	public boolean hasNext() {
		return chunkList.size() > 0;
	}
	
	@Override
	public Chunk next() {
		return chunkList.get(0);
	}
	
	@Override
	public void remove() {
		chunkList.remove(0);
	}
	
	public static ChunkIterator chunksInRegion(World world, int x1, int x2, int z1, int z2) {
		List<Chunk> chunks = new ArrayList<>();
		for (int x = x1; x <= x2; ++x) {
			for (int z = z1; z <= z2; ++z) {
				if (world.isChunkLoaded(x, z)) {
					chunks.add(world.getChunkAt(x, z));
				}
			}
		}
		return new ChunkIterator(chunks);
	}
	
	public static ChunkIterator chunksAround(Location loc, float radiusX, float radiusZ) {
		int x1 = (int) Math.floor((loc.getX() - radiusX - 2) / 16);
		int x2 = (int) Math.floor((loc.getX() + radiusX + 2) / 16);
		int z1 = (int) Math.floor((loc.getZ() - radiusZ - 2) / 16);
		int z2 = (int) Math.floor((loc.getZ() + radiusZ + 2) / 16);
		return chunksInRegion(loc.getWorld(), x1, x2, z1, z2);
	}
	
	public static ChunkIterator chunksAround(Location loc, int radius) {
		return chunksAround(loc, radius, radius);
	}
	
}
