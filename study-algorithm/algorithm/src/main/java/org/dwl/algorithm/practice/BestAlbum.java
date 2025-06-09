package org.dwl.algorithm.practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42579
 */
public class BestAlbum {
    public int[] solution(String[] genres, int[] plays) {
        HashMap<String, Songs> map = new HashMap<>();
        for (int i = 0; i < genres.length; i++) {
            map.computeIfAbsent(genres[i], k -> new Songs(k))
                    .addSongs(new Song(i, plays[i]));
        }

        List<Songs> songs = map.values().stream()
                .sorted(Comparator.comparingInt(Songs::getTotalPlay).reversed())
                .toList();

        List<Integer> album = new ArrayList<>();
        songs.forEach(song -> {
            List<Song> songList = song.getSongs();
            if (songList.size() < 2) {
                album.add(songList.get(0).getIdx());
                return;
            }

            List<Integer> idexes = songList.stream()
                    .sorted()
                    .limit(2)
                    .map(Song::getIdx)
                    .collect(Collectors.toList());

            album.add(idexes.get(0));
            album.add(idexes.get(1));
        });


        return album.stream().mapToInt(Integer::intValue).toArray();
    }

    public class Songs {
        private String genre;
        private int totalPlay;
        private List<Song> songs;

        public Songs(String genre) {
            this.genre = genre;
            this.totalPlay = 0;
            this.songs = new ArrayList<>();
        }

        public int getTotalPlay() {
            return totalPlay;
        }

        public List<Song> getSongs() {
            return songs;
        }

        public void addSongs(Song song) {
            songs.add(song);
            this.totalPlay += song.play;
        }
    }

    public class Song implements Comparable<Song> {

        private int idx;
        private int play;

        public Song(int idx, int play) {
            this.idx = idx;
            this.play = play;
        }

        public int getPlay() {
            return play;
        }

        public int getIdx() {
            return idx;
        }

        @Override
        public int compareTo(Song o) {
            if (o.play == this.play) {
                return this.idx - o.idx;
            }
            return o.play - this.play;
        }
    }
}
