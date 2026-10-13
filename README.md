# LogFlow v3

LogFlow v3, web sunucusu loglarını sıralı bir pipeline ile okuyan, ayrıştıran,
filtreleyen, seçilen alanlara indirgeyen ve UTF-8 bir çıktı dosyasına yazan Java
uygulamasıdır.

Akış sırası şöyledir:

`FileLineSource -> ParserStage -> PredicateFilterStage(ler) -> ProjectionStage -> FileSink`

## Gereksinimler

- Java JDK 21 veya daha yeni bir sürüm
- Testleri Maven ile çalıştırmak için Maven 3.9+

## Derleme

Proje ana klasöründe:

```bash
javac -d out src/*.java
```

## Çalıştırma

```bash
java -cp out Main <giriş> <çıkış> [seçenekler]
```

Örnek:

```bash
java -cp out Main data/access-small.log result.log \
  --min-status 400 \
  --path-prefix /api \
  --fields timestamp,clientIp,status,path
```

Seçenekler:

- `--min-status N`: Durum kodu `N` veya daha büyük olan kayıtları geçirir.
- `--path-prefix PREFIX`: Yolu verilen değerle başlayan kayıtları geçirir.
- `--fields a,b,c`: Çıktıda tutulacak alanları ve sıralarını belirler.

Filtre seçenekleri verilmezse tüm geçerli kayıtlar yazılır. `--fields`
verilmezse `timestamp,clientIp,status,path` alanları kullanılır. Desteklenen alanlar:
`timestamp`, `clientIp`, `method`, `path`, `status`, `bytes`, `userAgent`,
`attributes`, `raw`.

## Testler

```bash
mvn test
```

Test paketi parser testlerine ek olarak `PredicateFilterStage`,
`ProjectionStage`, `FileSink` birim testlerini ve üç aşamalı pipeline entegrasyon
testini içerir. JaCoCo raporu `target/site/jacoco/index.html` altında oluşur.

## Temel dosyalar

- `src/PipelineBuilder.java`: Tip güvenli fluent pipeline kurucusu.
- `src/PredicateFilterStage.java`: `Predicate<LogRecord>` ile kayıt filtreler.
- `src/ProjectionStage.java`: Yalnızca seçilen alanları bırakır.
- `src/FileSink.java`: Çıktıyı yaşam döngüsüne uygun biçimde dosyaya yazar.
- `src/CliOptions.java`: Komut satırı seçeneklerini doğrular.
- `ARCHITECTURE.md`: Tasarım kararları ve aşama sırası deneyi.

## Sürüm

Bu proje Increment 3 teslimidir ve Git etiketi `v3` olarak hazırlanmıştır.
