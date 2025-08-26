package com.example.perfume_ecommerce.controller;

//@RestController
//@RequestMapping("/api/variants")
//@RequiredArgsConstructor
public class ProductVariantController {

//    private final ProductVariantRepository variantRepository;
//    private final ProductRepository productRepository;
//
//    @GetMapping("/product/{productId}")
//    public List<ProductVariant> getVariantsByProduct(@PathVariable Long productId) {
//        return variantRepository.findByProductId(productId);
//    }
//
//    @PostMapping("/product/{productId}")
//    public ResponseEntity<ProductVariant> createVariant(@PathVariable Long productId,
//                                                        @RequestBody ProductVariant variant) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        variant.setProduct(product);
//        return ResponseEntity.ok(variantRepository.save(variant));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductVariant> updateVariant(@PathVariable Long id,
//                                                        @RequestBody ProductVariant variantDetails) {
//        return variantRepository.findById(id).map(variant -> {
//            variant.setVolume(variantDetails.getVolume());
//            variant.setPrice(variantDetails.getPrice());
//            variant.setStockQuantity(variantDetails.getStockQuantity());
//            variant.setSku(variantDetails.getSku());
//            return ResponseEntity.ok(variantRepository.save(variant));
//        }).orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
//        if (!variantRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        variantRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
}
