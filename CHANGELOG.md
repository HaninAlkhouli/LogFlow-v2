# Değişiklik Günlüğü

## v3

- Farklı veri tiplerini sıralı bağlayan fluent `PipelineBuilder` eklendi.
- Predicate tabanlı `PredicateFilterStage` eklendi.
- Alan seçimi ve veri minimizasyonu için `ProjectionStage` ile `ProjectedRecord` eklendi.
- `open()` / `close()` yaşam döngüsünü kullanan UTF-8 `FileSink` eklendi.
- `--min-status`, `--path-prefix` ve `--fields` CLI seçenekleri eklendi.
- Her yeni bileşen için birim testleri ve üç aşamalı entegrasyon testi eklendi.
- Filtre sırası deneyi ve sonucu `ARCHITECTURE.md` içinde belgelendi.
- Maven proje sürümü `3.0.0` olarak güncellendi.

## v2

Increment 2 kapsamında değiştirilen ve eklenen dosyalar:

- `src/LogRecord.java` - Immutable alan kaydı.
- `src/ParserStage.java` - CLF/Combined Log Format ayrıştırıcısı ve hatalı kayıt sayacı.
- `src/ConsoleSink.java` - `LogRecord` değerlerini tek satırda yazdırma.
- `src/Pipeline.java` - Tipli aşama listesi ve kaynak-mesaj akışı.
- `src/Main.java` - ParserStage eklenmesi ve hatalı kayıt sayısının gösterilmesi.
- `src/Record.java` - Domain kayıtları için ortak marker arayüzü.
- `src/test/java/ParserStageTest.java` - JUnit 5 parser testleri.
- `pom.xml` - JUnit 5, Maven ve JaCoCo yapılandırması.
- `data/access-small.log` - Combined Log Format örnek verileri.
- `README.md` - Çalıştırma, test ve coverage açıklamaları.
- `ARCHITECTURE.md` - Increment 2 mimari açıklaması.
- `.gitignore` - Maven `target` klasörü.
